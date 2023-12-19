package festifind.festifind.service;

import festifind.festifind.entity.Comment;
import festifind.festifind.entity.Event;
import festifind.festifind.entity.Rating;
import festifind.festifind.entity.User;
import festifind.festifind.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentDataFakerService {

    private final CommentRepository commentRepository;
    private final UserDataFakerService userDataFakerService;
    private final EventDataService eventDataService;

    public void saveComments(int count) {
        List<Comment> comments = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            Comment comment = new Comment();
            comment.setContent(faker.lorem().sentence());
            comment.setRating(Rating.values()[faker.random().nextInt(Rating.values().length)]);
            comment.setCreatedAt(LocalDateTime.now());

            User user = userDataFakerService.generateRandomUser();
            comment.setUser(user);

            Event event = eventDataService.generateRandomEvent();
            comment.setEvent(event);

            comments.add(comment);
        }

        commentRepository.saveAll(comments);
    }
}
