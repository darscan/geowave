package mil.nga.giat.geowave.core.store.memory;

import java.util.HashMap;
import java.util.Map;

import mil.nga.giat.geowave.core.store.DataStore;
import mil.nga.giat.geowave.core.store.StoreFactoryOptions;

public class MemoryDataStoreFactory extends
		AbstractMemoryStoreFactory<DataStore>
{
	private static final Map<String, DataStore> DATA_STORE_CACHE = new HashMap<String, DataStore>();

	@Override
	public DataStore createStore(
			final StoreFactoryOptions configOptions ) {
		return createStore(
				configOptions.getGeowaveNamespace());
	}

	protected static synchronized DataStore createStore(
			final String namespace ) {
		DataStore store = DATA_STORE_CACHE.get(
				namespace);
		if (store == null) {
			store = new MemoryDataStore(
					MemoryIndexStoreFactory.createStore(
							namespace),
					MemoryAdapterStoreFactory.createStore(
							namespace),
					MemoryDataStatisticsStoreFactory.createStore(
							namespace),
					MemoryAdapterIndexMappingStoreFactory.createStore(
							namespace),
					MemorySecondaryIndexStoreFactory.createStore(
							namespace));
			DATA_STORE_CACHE.put(
					namespace,
					store);
		}
		return store;
	}
}
