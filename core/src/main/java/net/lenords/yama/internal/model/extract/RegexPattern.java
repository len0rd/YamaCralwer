package net.lenords.yama.internal.model.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.lenords.yama.util.StrUtils;
import net.lenords.yama.internal.model.extract.RegexExtractor.ExtractorType;

/**
 * Represents a regular expression pattern, with constant regular expressions as well as portions of
 * the expression you want extracted into variables.
 *
 * <p>Variables you want from the pattern are returned upon a call to the {@link #run(String)}
 * method.
 *
 * <p>To build a regex to run, make properly ordered calls to the {@link #add(String)} and {@link
 * #add(RegexExtractor)} methods.
 *
 * <p>Example: Build a RegexPattern to match the following: <br>
 * "&lt;h1&gt;~@fooBar@~&lt;/h1&gt;" <br>
 * Where '~@fooBar@~' is the variable we want to extract and it has a regex like this:
 * "[^&lt;&gt;]*".
 *
 * <p>In order to properly build a RegexPattern, you need to sequentially call the 'add' methods
 * like so: <br>
 * pattern.add("&lt;h1&gt;").add(new Extractor("fooBar", "[^&lt;&gt;]*")).add("&lt;/h1&gt;") <br>
 * Once you've added all the pieces of your extractor in order, call {@link #build()} and then
 * {@link #run(String)} to get a map with fooBar and it's extracted value.
 *
 * @author len0rd
 * @since 2018-03-16
 */
public class RegexPattern implements ExtractionPattern<String> {
	private List<RegexExtractor> extractors;
	private List<String> extractorNamesToRetrieve;
	private String name;
	private String fullRegex;
	private Pattern compiledRegex;

	/**
	 * @param name Name of the Pattern
	 */
	public RegexPattern(String name) {
		this.name = name;
		this.extractors = new ArrayList<>();
		this.fullRegex = "";
		this.compiledRegex = null;
	}

	public RegexPattern() {
		this("Regex Extractor Pattern");
	}

	/**
	 * Add constant regex to the full pattern.
	 *
	 * @param regexConst The regular expression to add.
	 * @return This, the current RegexPattern
	 */
	public RegexPattern add(String regexConst) {
		extractors.add(new RegexExtractor(null, regexConst).setType(ExtractorType.NO_GROUP));
		return this;
	}

	public RegexPattern add(RegexExtractor regexVar) {
		extractors.add(regexVar);
		return this;
	}

	public RegexPattern add(String variableName, String variableRegex) {
		extractors.add(new RegexExtractor(variableName, variableRegex));
		return this;
	}

	public List<RegexExtractor> getExtractors() {
		return extractors;
	}

	public String build() {
		StringBuilder regex = new StringBuilder();
		extractorNamesToRetrieve = new ArrayList<>();

		final int extractorsSize = extractors.size();
		for (int i = 0; i < extractorsSize; i++) {
			RegexExtractor extractor = extractors.get(i);

			if (extractor.getType() == ExtractorType.NO_GROUP) {
				// then we dont need to make a special group for this
				//TODO:Add non-matching qualifier here??
				regex.append(extractor.getValue());
			} else {
				// then this extractor has a group we want to match
				String extractorName = "n" + i;
				regex
					.append("(?<")
					.append(extractorName)
					.append('>')
					.append(extractor.getValue())
					.append(')');
				extractorNamesToRetrieve.add(extractorName);
			}
		}

		this.fullRegex = regex.toString();
		this.compiledRegex = Pattern.compile(fullRegex);
		return fullRegex;
	}

	@Override
	public ExtractionResult run(String matchAgainst) {
		assert compiledRegex != null : "RegexPattern should first be built by calling the .build() method";

		List<Map<String, String>> results = new ArrayList<>();

		if (matchAgainst != null) {
			Matcher matcher = compiledRegex.matcher(matchAgainst);
			while (matcher.find()) {
				Map<String, String> singleMatch = new HashMap<>();

				for (String name : extractorNamesToRetrieve) {
					String result = matcher.group(name);
					if (!StrUtils.isNullEmpty(result)) {
						int extractorIndex = Integer.valueOf(name.substring(1, name.length()));
						//String keyName = extractors.get(extractorIndex).getKey();
						// run tidiers for this extractor, if any:
						singleMatch.putAll(extractors.get(extractorIndex).runTidiersAndSubextractors(result));
					}
				}
				results.add(singleMatch);
			}
		}

		return new ExtractionResult(results);
	}

	@Override
	public ExtractionResult buildAndRun(String matchAgainst) {
		build();
		return run(matchAgainst);
	}

	@Override
	public List<String> getExtractorNamesToRetrieve() {
		return extractorNamesToRetrieve;
	}

	public String getGeneratedRegex() {
		return fullRegex;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
