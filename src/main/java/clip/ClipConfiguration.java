package clip;

import generator.Generator;
import storage.Storage;
import utils.IConverter;

/**
 * The <code>ClipConfiguration</code> is used to store
 * Clip program runtime configuration.
 *
 * @author Guenael Thiriet
 */

class ClipConfiguration {

    /**
     * A bijective converter class
     */
    private final IConverter converter;

    /**
     * A unique identifier generator
     */
    private final Generator idGenerator;

    /**
     * A storage implementation, which is used to persist Clip data
     */
    private final Storage storage;

    /**
     * @param converter   The converter used by Clip.
     * @param idGenerator The generator used by Clip.
     * @param storage     The storage used by Clip.
     */
    public ClipConfiguration(IConverter converter, Generator idGenerator, Storage storage) {
        this.converter = converter;
        this.idGenerator = idGenerator;
        this.storage = storage;
    }

    /**
     * @return the converter of this object instance.
     */
    public IConverter getConverter() {
        return converter;
    }

    /**
     * @return the generator of this object instance.
     */
    public Generator getIdGenerator() {
        return idGenerator;
    }

    /**
     * @return the storage of this object instance.
     */
    public Storage getStorage() {
        return storage;
    }


}
