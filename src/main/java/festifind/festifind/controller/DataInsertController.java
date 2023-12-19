package festifind.festifind.controller;

import festifind.festifind.service.CommentDataFakerService;
import festifind.festifind.service.LikeEventFakerService;
import festifind.festifind.service.UserDataFakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DataInsertController {

    private final UserDataFakerService userDataFakerService;
    private final CommentDataFakerService commentDataFakerService;
    private final LikeEventFakerService likeEventFakerService;

    @PostMapping("api/users")
    private void getUsers(){
        userDataFakerService.saveUsers(100);
    }

    @PostMapping("api/comments")
    private void getComments(){
        commentDataFakerService.saveComments(100);
    }

    @PostMapping("api/likeEvents")
    private void getLikeEvents(){
        likeEventFakerService.saveLikeEvents(300);
    }


}
