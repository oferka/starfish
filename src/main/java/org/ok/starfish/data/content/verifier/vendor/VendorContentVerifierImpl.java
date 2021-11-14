package org.ok.starfish.data.content.verifier.vendor;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.vendor.VendorService;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VendorContentVerifierImpl implements VendorContentVerifier {

    private final VendorService vendorService;

    public VendorContentVerifierImpl(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Override
    public Iterable<Vendor> findLoaded(Iterable<Vendor> vendors) {
        List<Vendor> result = new ArrayList<>();
        for(Vendor vendor : vendors) {
            if(vendorService.exists(vendor)) {
                result.add(vendor);
            }
        }
        log.info("{} vendors found as loaded", result.size());
        return result;
    }

    @Override
    public Iterable<Vendor> findNotLoaded(Iterable<Vendor> vendors) {
        List<Vendor> result = new ArrayList<>();
        for(Vendor vendor : vendors) {
            if(!vendorService.exists(vendor)) {
                result.add(vendor);
            }
        }
        log.info("{} vendors found as unloaded", result.size());
        return result;
    }
}
