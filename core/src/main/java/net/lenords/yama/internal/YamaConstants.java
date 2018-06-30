package net.lenords.yama.internal;


import java.util.regex.Pattern;

/**
 * Static objects commonly used across the code base
 *
 * @author len0rd
 * @since 2018-06-29
 */
public class YamaConstants {

	public static final Pattern TOKEN_PATTERN = Pattern.compile("~#(.+?)#~");
}
