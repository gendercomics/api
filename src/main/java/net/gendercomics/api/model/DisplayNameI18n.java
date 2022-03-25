package net.gendercomics.api.model;

import org.springframework.data.annotation.Transient;

import java.util.Map;

public interface DisplayNameI18n {

    @Transient
    Map<Language, String> getDisplayNames();

}
