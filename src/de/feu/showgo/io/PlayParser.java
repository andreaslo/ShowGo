package de.feu.showgo.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import de.feu.showgo.model.Act;
import de.feu.showgo.model.Paragraph;
import de.feu.showgo.model.ParseElement;
import de.feu.showgo.model.ParseResult;
import de.feu.showgo.model.Passage;
import de.feu.showgo.model.Role;
import de.feu.showgo.model.Scene;
import de.feu.showgo.model.StageDirection;
import de.feu.showgo.model.TheaterPlay;

public class PlayParser {

	private static final Logger log = Logger.getLogger(PlayParser.class);

	public ParseResult validate(String input) {

		log.debug("validating");
		ParseResult result = new ParseResult();
		result.setValid(true);

		Stack<String> validationStack = new Stack<String>();
		Pattern p = Pattern.compile("(<!--([^/]\\w*?)-->|<!--/(\\w+?)-->)", Pattern.DOTALL);
		Matcher m = p.matcher(input);
		boolean firstRun = true;

		while (m.find()) {

			if (m.group(2) != null) {
				// start tag
				String openingTag = m.group(2);
				log.trace("Starttag: " + openingTag);

				if (!firstRun) {
					if (validationStack.isEmpty()) {
						result.setValid(false);
						result.setErrorMessage("Second root element found: " + openingTag);
						result.setErrorLine(StringUtil.countOccurrences(input.substring(0, m.end() - 1), '\n') + 1);
						break;
					}
				}
				firstRun = false;

				validationStack.push(openingTag);
			} else if (m.group(3) != null) {
				// end tag
				String closingTag = m.group(3);
				log.trace("Endtag: " + closingTag);

				String top = null;
				try {
					top = validationStack.pop();
				} catch (EmptyStackException e) {
					result.setValid(false);
					result.setErrorMessage("No opening tag for " + closingTag);
					result.setErrorLine(StringUtil.countOccurrences(input.substring(0, m.end() - 1), '\n') + 1);
					break;
				}

				if (!closingTag.equals(top)) {
					result.setValid(false);
					result.setErrorMessage("No closing tag for " + top);
					result.setErrorLine(StringUtil.countOccurrences(input.substring(0, m.end() - 1), '\n') + 1);
					break;
				}
			} else {
				// should not happen
				log.error("parsing error");
				result.setValid(false);
				result.setErrorMessage("Parsing error");
				break;
			}
		}

		if (result.isValid() && !validationStack.isEmpty()) {
			String top = validationStack.pop();
			result.setValid(false);
			result.setErrorMessage("No closing tag for " + top);
			result.setErrorLine(0); // origin of wrong tag is unknown
		}

		log.debug("validation complete, result: " + result);

		return result;
	}

	public ParseElement parse(String play) throws ParsingException {
		ParseResult parseResult = validate(play);
		if (!parseResult.isValid()) {
			throw new ParsingException(parseResult.toString());
		}

		ParseElement thisElement = new ParseElement();
		Pattern elementPattern = Pattern.compile("<!--(.*?)-->(.*)<!--/\\1-->", Pattern.DOTALL);
		Matcher rootMatcher = elementPattern.matcher(play);

		if (rootMatcher.find()) {
			String tagName = rootMatcher.group(1);
			log.trace("tag: " + tagName);
			String content = rootMatcher.group(2);
			log.trace("contentWithChildren: " + content);

			String text = extractText(content);
			log.trace("text: " + text);

			thisElement.setTagName(tagName);
			thisElement.setText(text);

			List<String> children = extractChildren(content);
			log.trace("children: " + children);
			for (String child : children) {
				thisElement.addChild(parse(child));
			}

		}

		return thisElement;
	}

	private String extractText(String input) {
		int levelCounter = 0;
		StringBuilder output = new StringBuilder();

		Pattern p = Pattern.compile("(<!--([^/]\\w*?)-->|<!--/(\\w+?)-->)", Pattern.DOTALL);
		Matcher m = p.matcher(input);

		int lastIndex = 0;

		while (m.find()) {

			if (m.group(2) != null) {
				// start tag
				if (levelCounter == 0) {
					output.append(input.substring(lastIndex, m.start()));
				}

				levelCounter++;
			} else if (m.group(3) != null) {
				// end tag
				lastIndex = m.end();
				levelCounter--;
			} else {
				// should not happen
				log.error("parsing error");
				break;
			}
		}

		output.append(input.subSequence(lastIndex, input.length()));

		return output.toString();
	}

	private List<String> extractChildren(String input) {
		int levelCounter = 0;

		List<String> output = new ArrayList<String>();

		Pattern p = Pattern.compile("(<!--([^/]\\w*?)-->|<!--/(\\w+?)-->)", Pattern.DOTALL);
		Matcher m = p.matcher(input);

		int lastIndex = 0;

		while (m.find()) {
			if (m.group(2) != null) {
				// start tag
				log.trace("starttag " + m.group(2));
				log.trace("counter " + levelCounter);
				if (levelCounter == 0) {
					lastIndex = m.start();
				}

				levelCounter++;
			} else if (m.group(3) != null) {
				// end tag
				log.trace("endtag " + m.group(3));

				levelCounter--;
				log.trace("counter " + levelCounter);
				if (levelCounter == 0) {
					log.trace("adding " + lastIndex + " " + m.end());
					output.add(input.substring(lastIndex, m.end()));
				}
			} else {
				// should not happen
				log.error("parsing error");
				break;
			}
		}

		return output;
	}

	public TheaterPlay generatePlay(File inputFile) throws IOException, ParsingException {
		String input = FileUtil.readFile(inputFile);
		return generatePlay(parse(input));
	}

	public TheaterPlay generatePlay(ParseElement rootElement) throws ParsingException {
		TheaterPlay play = new TheaterPlay();

		if (!"stueck".equals(rootElement.getTagName())) {
			throw new ParsingException("Root element is not of type 'stueck'. Element: " + rootElement);
		}

		Map<String, Role> roles = new HashMap<String, Role>();

		play.setName(rootElement.getText().trim());
		for (ParseElement child : rootElement.getChildren()) {
			play.addAct(parseAct(child, roles));
		}

		List<Role> rolesList;
		if (roles.values() instanceof List) {
			rolesList = (List<Role>) roles.values();
		} else {
			rolesList = new ArrayList<Role>(roles.values());
		}
		play.setRoles(rolesList);

		PseudoRoleRecognition recognizer = new PseudoRoleRecognition();
		recognizer.recognizePseudoRoles(play);
		return play;
	}

	private Act parseAct(ParseElement actElement, Map<String, Role> roles) throws ParsingException {
		Act act = new Act();

		if (!"aufzug".equals(actElement.getTagName())) {
			throw new ParsingException("Act element is not defined by a 'aufzug' tag name. Element: " + actElement);
		}

		act.setName(StringUtil.sanitizeText(actElement.getText()));

		for (ParseElement child : actElement.getChildren()) {
			act.addScene(parseScene(child, roles));
		}

		return act;
	}

	private Scene parseScene(ParseElement sceneElement, Map<String, Role> roles) throws ParsingException {
		Scene scene = new Scene();

		if (!"szene".equals(sceneElement.getTagName())) {
			throw new ParsingException("Scene element is not defined by a 'szene' tag name. Element: " + sceneElement);
		}

		scene.setName(StringUtil.sanitizeText(sceneElement.getText()));
		Role lastRole = null; // A paragraph might not contain a role if it is
								// not the first one, see discussion in
								// newsgroup
		Role sceneAll = new Role(); // The role "alle" for this scene. This is
									// the only scene dependent role, all other
									// one are global
		sceneAll.setName("ALLE");
		for (ParseElement child : sceneElement.getChildren()) {
			Paragraph paragraph = parseParagraph(child, lastRole, roles, sceneAll);

			if (paragraph instanceof Passage) {
				lastRole = ((Passage) paragraph).getRole();
				if (lastRole == sceneAll) {
					scene.setAllRole(sceneAll);
				}
			}

			scene.addParagraphs(paragraph);
		}

		return scene;
	}

	private Paragraph parseParagraph(ParseElement paragraphElement, Role lastRole, Map<String, Role> roles, Role sceneAll) throws ParsingException {
		if (!"regie".equals(paragraphElement.getTagName()) && !"passage".equals(paragraphElement.getTagName())) {
			throw new ParsingException("Paragraph element is not defined by a 'passage' or 'regie' tag name. Element: " + paragraphElement);
		}

		if ("regie".equals(paragraphElement.getTagName())) {
			return parseStageDirection(paragraphElement);
		} else if ("passage".equals(paragraphElement.getTagName())) {
			return parsePassage(paragraphElement, lastRole, roles, sceneAll);
		} else {
			// should definitely not happen
			return null;
		}
	}

	private StageDirection parseStageDirection(ParseElement paragraphElement) throws ParsingException {
		StageDirection stageDirection = new StageDirection();

		if (!"regie".equals(paragraphElement.getTagName())) {
			throw new ParsingException("Stage direction element is not defined by a 'regie' tag name. Element: " + paragraphElement);
		}

		stageDirection.setText(paragraphElement.getText());

		return stageDirection;
	}

	private Passage parsePassage(ParseElement passageElement, Role lastRole, Map<String, Role> roles, Role sceneAll) throws ParsingException {
		Passage passage = new Passage();
		if (!"passage".equals(passageElement.getTagName())) {
			throw new ParsingException("Passage element is not defined by a 'passage' tag name. Element: " + passageElement);
		}

		passage.setText(passageElement.getText());

		if (passageElement.getChildren().size() != 1) {
			log.debug("no role assigned for " + passageElement.getText());
			if (lastRole == null) {
				throw new ParsingException("First passage in a scene does not declare a role, element text:" + passageElement.getText());
			}

			passage.setRole(lastRole);
		} else {
			Role newRole = parseRole(passageElement.getChildren().get(0));

			if (newRole.equals(sceneAll)) {
				passage.setRole(sceneAll);
				return passage;
			}

			if (!roles.containsKey(newRole.getName())) {
				roles.put(newRole.getName(), newRole);
			}

			passage.setRole(roles.get(newRole.getName()));
		}

		return passage;
	}

	private Role parseRole(ParseElement roleElement) throws ParsingException {
		Role role = new Role();
		if (!"rolle".equals(roleElement.getTagName())) {
			throw new ParsingException("Role element is not defined by a 'rolle' tag name. Element: " + roleElement);
		}

		String roleName = StringUtil.sanitizeRoleName(roleElement.getText());

		role.setName(roleName);

		return role;
	}


}
