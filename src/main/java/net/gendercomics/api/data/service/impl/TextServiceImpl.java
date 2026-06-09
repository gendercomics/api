package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.TextRepository;
import net.gendercomics.api.data.service.TextService;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TextServiceImpl implements TextService {

    private final TextRepository _textRepository;

    @Override
    public Text save(Text text, String userName) {
        if (text.getId() == null) {
            text.setMetaData(new MetaData());
            text.getMetaData().setCreatedOn(new Date());
            text.getMetaData().setCreatedBy(userName);
            return _textRepository.insert(text);
        }
        text.getMetaData().setChangedOn(new Date());
        text.getMetaData().setChangedBy(userName);
        return _textRepository.save(text);
    }

    @Override
    public void delete(String id) {
        _textRepository.deleteById(id);
    }
}
