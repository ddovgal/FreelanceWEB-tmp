package com.project.controller;

import com.project.businesslogic.DialogMessage;
import com.project.businesslogic.user.User;
import com.project.dao.DialogMessageDAO;
import com.project.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас - контролер. Саме в цьому класі виконуеться обробка запитів, та видача відповідей на запит. Данний контролер
 * оброблюе запити, пов'язані з діалоговими повідомленнями.
 */
@Controller
@RequestMapping("/messages")
public class DialogMessagesController {

    private DialogMessageDAO dialogMessageDAO;
    private UserDAO userDAO;

    @Autowired
    public void setDialogMessageDAO(DialogMessageDAO dialogMessageDAO) {
        this.dialogMessageDAO = dialogMessageDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Редірект до сторінкі повідомлень вперше, вибір співрозмовника.
     * @param myId id користувача, що зараз перейшов до повідомлень
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ModelAndView showAll(@RequestParam(value = "myId") Long myId){
        List<User> interlocutors = dialogMessageDAO.getAllInterlocutors(myId, null, null);
        ModelAndView modelAndView =  new ModelAndView("public/messages");
        modelAndView.addObject("isFromRefresh", false);
        modelAndView.addObject("interlocutors", interlocutors);
        return modelAndView;
    }

    /**
     * Оновлення сторінкі повідомлень, показ повідомленнь для первного співрозмовника.
     * @param principal об'єкт Spring security
     * @param interlocutorId id первного співрозмовника
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/show_for/{interlocutorId}", method = RequestMethod.GET)
    public ModelAndView getCustomerImage(Principal principal,
                                         @PathVariable("interlocutorId") Long interlocutorId) throws IOException {
        ModelAndView modelAndView =  new ModelAndView("public/messages");

        Long myId = userDAO.getByEmail(principal.getName()).getId();
        List<User> interlocutors = dialogMessageDAO.getAllInterlocutors(myId, null, null);

        if (interlocutors.contains( userDAO.get(interlocutorId))){
            List<DialogMessage> messages = dialogMessageDAO.getMessagesForInterlocutor(myId, interlocutorId, 0, 25);
            modelAndView.addObject("messages", messages);
        } else {
            interlocutors.add(0, userDAO.get(interlocutorId));
            modelAndView.addObject("messages", new ArrayList<>());
        }

        modelAndView.addObject("isFromRefresh", true);
        modelAndView.addObject("myId", myId);
        modelAndView.addObject("interlocutorId", interlocutorId);
        modelAndView.addObject("interlocutors", interlocutors);

        return modelAndView;
    }

    /**
     * Відправка повідомлення від данного корістувача його поточному співрозмовнику.
     * @param body об'єкт, що несе усі данні
     * @return модель-представлення сторінки
     */
    @RequestMapping(value = "/send", method = RequestMethod.PUT)
    public HttpStatus sendMessage(@RequestBody MultiValueMap<String,String> body){
        try {
            Long senderId = Long.valueOf(body.getFirst("myId"));
            Long receiverId = Long.valueOf(body.getFirst("receiverId"));
            String text = String.valueOf(body.getFirst("text"));
            dialogMessageDAO.realCreate(senderId, receiverId, text);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

}
