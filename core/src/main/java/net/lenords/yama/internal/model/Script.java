package net.lenords.yama.internal.model;

import net.lenords.yama.internal.conf.ScriptLanguage;

import java.util.Map;

/**
 * Custom script that can be run at any callback point in a crawl
 *
 * @author len0rd
 * @since 2017-07-03
 */
public class Script implements Action<ScriptResult> {

	private String name;
	private ScriptLanguage lang;
	private String toExecute;

	public Script(String name, ScriptLanguage lang, String toExecute) {
		this.name = name;
		this.lang = lang;
		this.toExecute = toExecute;
	}


	public void setName(String name) {
		this.name = name;
	}

	public ScriptLanguage getLang() {
		return lang;
	}

	public void setLang(ScriptLanguage lang) {
		this.lang = lang;
	}

	public String getToExecute() {
		return toExecute;
	}

	public void setToExecute(String toExecute) {
		this.toExecute = toExecute;
	}

	@Override
	public ScriptResult run(Map<String, Object> context) {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}
}
