package storage.persistent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Guenael Thiriet on 2016-04-17.
 */

@Entity
public class ShortUrlEntity {

    private @Id @GeneratedValue Long id;
    private final String identifier, longUrl;

    private ShortUrlEntity() {
        identifier = null;
        longUrl = null;
    }

    public ShortUrlEntity(String identifier, String longUrl) {
        this.identifier = identifier;
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return String.format(
                "ShortUrlEntity[id=%d, identifier='%s', longUrl='%s']",
                id, identifier, longUrl);
    }

    public String getIdentifier(){
        return identifier;
    }

    public String getLongUrl() {
        return longUrl;
    }
}