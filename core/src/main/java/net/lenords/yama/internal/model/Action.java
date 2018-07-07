package net.lenords.yama.internal.model;

import java.util.Map;

/**
 * The basis for all user defined actions in a Crawler.
 * Actions come in 3 main flavors: Nav, Extract and Script.
 * <i>Only Yama developers should directly interact with or manipulate this interface</i>
 *
 * <p>
 * <b>Nav:</b> Or Navigation. Navigation actions tell the crawler
 *    to navigate the crawler to a new url, new page, or to click a button.
 * <p>
 * <b>Extract:</b> Extract actions tell the crawler try and extract a piece of
 *    data given a particular context. Generally these actions are only defined
 *    in a page and run automatically from there, however the user is also given
 *    the option to define these manually.
 * <p>
 * <b>Script:</b> Script actions release the true power of Yama. With them,
 *    users are able to script custom actions for their Crawler using a
 *    variety of languages. Script actions are granted certain context,
 *    so that they can link into the active crawler they're associated with.
 *
 * @param <R>  Output from the action upon run. Should not be null regardless of
 *             success. Must implement ActionResult.
 *
 * @see ActionResult
 * @author len0rd
 */
public interface Action<R extends ActionResult> {

	R run(Map<String, Object> context);

	String getName();
}
