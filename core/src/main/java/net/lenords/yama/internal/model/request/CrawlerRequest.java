package net.lenords.yama.internal.model.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents load request for crawler
 *
 * @author len0rd
 */
public class CrawlerRequest {

	private String url;
	private int requestTimeout;
	private List<HttpParam> params;
	private String encoding;
	//private List<> cookies

	public CrawlerRequest(String url) {
		this.url = url;
		this.requestTimeout = -1;
		this.params = new ArrayList<>();
		this.encoding = "UTF-8";
	}

	public CrawlerRequest(CrawlerRequest requestToCopy) {
		this.url = requestToCopy.getBaseUrl();
		this.requestTimeout = requestToCopy.getRequestTimeout();
		this.params = requestToCopy.getGetParams();
		this.encoding = requestToCopy.getEncoding();
	}

	public String buildUrl() {
		StringBuilder fullURL = new StringBuilder(url);
		if (!params.isEmpty()) {
			if (fullURL.indexOf("?") < 0) {
				fullURL.append('?');
			}

			//there should always be at least 1 element here:
			Iterator<HttpParam> paramIter = params.iterator();
			fullURL.append(encodeParam(paramIter.next()));
			while (paramIter.hasNext()) {
				fullURL.append("&").append(encodeParam(paramIter.next()));
			}

		}

		return fullURL.toString();
	}

	public String getBaseUrl() {
		return url;
	}

	public void setBaseUrl(String url) {
		this.url = url;
	}

	public int getRequestTimeout() {
		return requestTimeout;
	}

	public void setGetParam(String key, String value) {
		Optional<HttpParam> paramMatch = params.stream().filter(p -> p.getKey().equals(key)).findFirst();

		if (paramMatch.isPresent()) {
			paramMatch.get().setValue(value);
		} else {
			params.add(new HttpParam(key, value));
		}

	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public List<HttpParam> getGetParams() {
		return params;
	}

	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public boolean hasTokens() {
		return url.matches(".*~#.+?#~.*") || paramsHaveTokens();
	}

	private boolean paramsHaveTokens() {
		for (HttpParam param : params) {
			if (param.getKey().matches(".*~#.+?#~.*") ||
				param.getValue().matches(".*~#.+?#~.*")) {
				return true;
			}
		}
		return false;
	}

	private String encodeParam(HttpParam param) {
		try {
			return URLEncoder.encode(param.getKey(), encoding) + "=" + URLEncoder.encode(param.getValue(), encoding);
		} catch (UnsupportedEncodingException e) {
			return param.getKey() + "=" + param.getValue();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CrawlerRequest that = (CrawlerRequest) o;
		return requestTimeout == that.requestTimeout &&
			Objects.equals(url, that.url) &&
			Objects.equals(params, that.params) &&
			Objects.equals(encoding, that.encoding);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url, requestTimeout, params, encoding);
	}

	@Override
	public String toString() {
		return "CrawlerRequest{" +
			"url='" + url + '\'' +
			", requestTimeout=" + requestTimeout +
			", params=" + params +
			", encoding='" + encoding + '\'' +
			'}';
	}
}
