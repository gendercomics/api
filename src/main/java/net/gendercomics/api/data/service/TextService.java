package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Text;

public interface TextService {

    Text save(Text text, String userName);

    void delete(String id);
}
