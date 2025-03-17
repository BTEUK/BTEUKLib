import net.bteuk.provider.BTEPlayerProvider;

module net.bteuk.services {
    exports net.bteuk.api;
    exports net.bteuk.serviceloader;
    exports net.bteuk.entity;
    exports net.bteuk.provider;
    exports net.bteuk.exception;

    uses BTEPlayerProvider;
}