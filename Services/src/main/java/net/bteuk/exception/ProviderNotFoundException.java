package net.bteuk.exception;

public class ProviderNotFoundException extends Exception {

    public ProviderNotFoundException(String providerName, String version) {
        super(String.format("Provider %s does not exist for version %s", providerName, version));
    }
}
