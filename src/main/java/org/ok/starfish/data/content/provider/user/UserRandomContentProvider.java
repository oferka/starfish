package org.ok.starfish.data.content.provider.user;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.content.provider.user.properties.*;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.user.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("random-users-provider")
public class UserRandomContentProvider implements UserContentProvider {

    private final AccountService accountService;

    private final IdProvider idProvider;

    private final UserGenderProvider userGenderProvider;

    private final UserTitleProvider userTitleProvider;

    private final UserFirstNameProvider userFirstNameProvider;

    private final UserLastNameProvider userLastNameProvider;

    private final UserStreetNumberProvider userStreetNumberProvider;

    private final UserStreetNameProvider userStreetNameProvider;

    private final UserCityProvider userCityProvider;

    private final UserStateProvider userStateProvider;

    private final UserCountryProvider userCountryProvider;

    private final UserPostcodeProvider userPostcodeProvider;

    private final UserLatitudeProvider userLatitudeProvider;

    private final UserLongitudeProvider userLongitudeProvider;

    private final UserTimezoneOffsetProvider userTimezoneOffsetProvider;

    private final UserTimezoneDescriptionProvider userTimezoneDescriptionProvider;

    private final UserEmailProvider userEmailProvider;

    private final UserDateOfBirthProvider userDateOfBirthProvider;

    private final UserDateOfRegistrationProvider userDateOfRegistrationProvider;

    private final UserPhoneProvider userPhoneProvider;

    private final UserCellProvider userCellProvider;

    private final UserLargePictureProvider userLargePictureProvider;

    private final UserMediumPictureProvider userMediumPictureProvider;

    private final UserThumbnailPictureProvider userThumbnailPictureProvider;

    private final UserNationalityProvider userNationalityProvider;

    private final CreationDateProvider creationDateProvider;

    public UserRandomContentProvider(AccountService accountService,
                                     IdProvider idProvider,
                                     UserGenderProvider userGenderProvider,
                                     UserTitleProvider userTitleProvider,
                                     UserFirstNameProvider userFirstNameProvider,
                                     UserLastNameProvider userLastNameProvider,
                                     UserStreetNumberProvider userStreetNumberProvider,
                                     UserStreetNameProvider userStreetNameProvider,
                                     UserCityProvider userCityProvider,
                                     UserStateProvider userStateProvider,
                                     UserCountryProvider userCountryProvider,
                                     UserPostcodeProvider userPostcodeProvider,
                                     UserLatitudeProvider userLatitudeProvider,
                                     UserLongitudeProvider userLongitudeProvider,
                                     UserTimezoneOffsetProvider userTimezoneOffsetProvider,
                                     UserTimezoneDescriptionProvider userTimezoneDescriptionProvider,
                                     UserEmailProvider userEmailProvider,
                                     UserDateOfBirthProvider userDateOfBirthProvider,
                                     UserDateOfRegistrationProvider userDateOfRegistrationProvider,
                                     UserPhoneProvider userPhoneProvider,
                                     UserCellProvider userCellProvider,
                                     UserLargePictureProvider userLargePictureProvider,
                                     UserMediumPictureProvider userMediumPictureProvider,
                                     UserThumbnailPictureProvider userThumbnailPictureProvider,
                                     UserNationalityProvider userNationalityProvider,
                                     CreationDateProvider creationDateProvider) {
        this.accountService = accountService;
        this.idProvider = idProvider;
        this.userGenderProvider = userGenderProvider;
        this.userTitleProvider = userTitleProvider;
        this.userFirstNameProvider = userFirstNameProvider;
        this.userLastNameProvider = userLastNameProvider;
        this.userStreetNumberProvider = userStreetNumberProvider;
        this.userStreetNameProvider = userStreetNameProvider;
        this.userCityProvider = userCityProvider;
        this.userStateProvider = userStateProvider;
        this.userCountryProvider = userCountryProvider;
        this.userPostcodeProvider = userPostcodeProvider;
        this.userLatitudeProvider = userLatitudeProvider;
        this.userLongitudeProvider = userLongitudeProvider;
        this.userTimezoneOffsetProvider = userTimezoneOffsetProvider;
        this.userTimezoneDescriptionProvider = userTimezoneDescriptionProvider;
        this.userEmailProvider = userEmailProvider;
        this.userDateOfBirthProvider = userDateOfBirthProvider;
        this.userDateOfRegistrationProvider = userDateOfRegistrationProvider;
        this.userPhoneProvider = userPhoneProvider;
        this.userCellProvider = userCellProvider;
        this.userLargePictureProvider = userLargePictureProvider;
        this.userMediumPictureProvider = userMediumPictureProvider;
        this.userThumbnailPictureProvider = userThumbnailPictureProvider;
        this.userNationalityProvider = userNationalityProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<User> get(int numberOfItems) {
        List<User> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getUser());
        }
        log.info("{} users provided", result.size());
        return result;
    }

    private @NotNull User getUser() {
        Optional<Account> account = accountService.findRandom();
        if(account.isPresent()) {
            return new User(
                    idProvider.getRandom(),
                    userGenderProvider.get(),
                    userTitleProvider.get(),
                    userFirstNameProvider.get(),
                    userLastNameProvider.get(),
                    userStreetNumberProvider.get(),
                    userStreetNameProvider.get(),
                    userCityProvider.get(),
                    userStateProvider.get(),
                    userCountryProvider.get(),
                    userPostcodeProvider.get(),
                    userLatitudeProvider.get(),
                    userLongitudeProvider.get(),
                    userTimezoneOffsetProvider.get(),
                    userTimezoneDescriptionProvider.get(),
                    userEmailProvider.get(),
                    userDateOfBirthProvider.get(),
                    userDateOfRegistrationProvider.get(),
                    userPhoneProvider.get(),
                    userCellProvider.get(),
                    userLargePictureProvider.get(),
                    userMediumPictureProvider.get(),
                    userThumbnailPictureProvider.get(),
                    userNationalityProvider.get(),
                    creationDateProvider.getNow(),
                    account.get()
            );
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }
}
