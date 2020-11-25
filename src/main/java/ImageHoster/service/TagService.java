package ImageHoster.service;

import ImageHoster.model.Tag;
import ImageHoster.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    public Tag getTagByName(final String title) {
        return tagRepository.findTag(title);
    }

    public Tag createTag(final Tag tag) {
        return tagRepository.createTag(tag);
    }
}
