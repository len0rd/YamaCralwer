package net.lenords.yama.model.extract;


import java.util.List;
import java.util.Map;

/**
 * Interface for extraction methods
 *
 * @author len0rd
 * @since 2018-03-21
 */
public interface ExtractionPattern<T> {

  T build();

  List<Map<String, String>> buildAndExecute(T matchAgainst);

  List<Map<String, String>> execute(T matchAgainst);

  List<String> getExtractorNamesToRetrieve();

  String getName();

}
