package storage.persistent;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Guenael Thiriet on 2016-04-17.
 */

public interface ShortUrlRepository extends CrudRepository<ShortUrlEntity, Long> {

    ShortUrlEntity findByIdentifier(String identifier);

    ShortUrlEntity findByLongUrl(String longUrl);
}