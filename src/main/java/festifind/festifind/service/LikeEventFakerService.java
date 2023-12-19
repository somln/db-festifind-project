package festifind.festifind.service;

import festifind.festifind.entity.Event;
import festifind.festifind.entity.LikeEvent;
import festifind.festifind.entity.User;
import festifind.festifind.repository.LikeEventRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeEventFakerService {

    private final LikeEventRepository likeEventRepository;
    private final UserDataFakerService userDataFakerService;
    private final EventDataService eventDataService;


    public void saveLikeEvents(int count) {
        List<LikeEvent> likeEvents = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            LikeEvent likeEvent = new LikeEvent();

            User user = userDataFakerService.generateRandomUser();
            likeEvent.setUser(user);

            Event event = eventDataService.generateRandomEvent();
            likeEvent.setEvent(event);

            likeEvents.add(likeEvent);
        }

        likeEventRepository.saveAll(likeEvents);
    }
}