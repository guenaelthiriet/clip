package clip.persistent;

import storage.InvalidArgumentException;
import storage.InvalidStateException;
import storage.Storage;

/**
 * Created by Guenael Thiriet on 2016-04-21.
 */
public class PersistentStorage implements Storage {


    final ShortUrlRepository repository;

    // Not usable
    private PersistentStorage() {
        this.repository = null;
    }

    public PersistentStorage(ShortUrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public void store(String key, String value) throws InvalidStateException, InvalidArgumentException {

        if ((key == null) || (value == null)) {
            throw new InvalidArgumentException("key or value are null");
        }
        if (hasKey(key)) {
            throw new InvalidStateException("Identifier already in container");
        }
        if (hasValue(value)) {
            throw new InvalidStateException("URL already in container");
        }
        repository.save(new ShortUrlEntity(key, value));
    }

    @Override
    public boolean hasKey(String key) {
        return repository.findByIdentifier(key) != null;
    }

    @Override
    public boolean hasValue(String value) {
        return repository.findByLongUrl(value) != null;
    }

    @Override
    public String getKey(String value) {
        return repository.findByLongUrl(value).getIdentifier();
    }

    @Override
    public String getValue(String key) {
        return repository.findByIdentifier(key).getLongUrl();
    }
}
