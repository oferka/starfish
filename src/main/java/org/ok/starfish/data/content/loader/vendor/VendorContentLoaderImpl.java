package org.ok.starfish.data.content.loader.vendor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.provider.vendor.VendorContentProvider;
import org.ok.starfish.data.content.verifier.vendor.VendorContentVerifier;
import org.ok.starfish.data.service.vendor.VendorService;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class VendorContentLoaderImpl implements VendorContentLoader {

    private final VendorContentProvider vendorContentProvider;
    private final VendorContentVerifier vendorContentVerifier;
    private final VendorService vendorService;

    public VendorContentLoaderImpl(VendorContentProvider vendorContentProvider,
                                   VendorContentVerifier vendorContentVerifier,
                                   VendorService vendorService) {
        this.vendorContentProvider = vendorContentProvider;
        this.vendorContentVerifier = vendorContentVerifier;
        this.vendorService = vendorService;
    }

    @Override
    public @NotNull Iterable<Vendor> ensureContentLoaded() {
        List<Vendor> content = vendorContentProvider.get(70);
        Iterable<Vendor> unloadedContent = vendorContentVerifier.findNotLoaded(content);
        if(!IterableUtils.isEmpty(unloadedContent)) {
            Iterable<Vendor> saved = vendorService.saveAll(unloadedContent);
            log.info("{} vendors saved", IterableUtils.size(saved));
        }
        log.info("{} vendors ensured loaded", content.size());
        return content;
    }
}
