package net.bteuk.serviceloader;

import net.bteuk.exception.ProviderNotFoundException;
import net.bteuk.provider.BTEPlayerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PlayerLoader {

    public static BTEPlayerProvider getBTEPlayerProvider(String version) throws ProviderNotFoundException {
        ServiceLoader<BTEPlayerProvider> loader = ServiceLoader.load(BTEPlayerProvider.class);
        for (BTEPlayerProvider provider : loader) {
            if (provider.getVersion().equals(version)) {
                return provider;
            }
        }
        throw new ProviderNotFoundException(BTEPlayerProvider.getName(), version);
    }
}
