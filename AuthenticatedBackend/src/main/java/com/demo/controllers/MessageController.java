package com.demo.controllers;

import com.demo.models.Message;
import com.demo.models.MessageType;
import com.demo.models.User;
import com.demo.repository.MessageRepository;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String helloMessageController() {
        return "Message access level";
    }

    @PostMapping("/add")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        try {

            Message savedMessage = messageRepository.save(message);
            return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            List<Message> messages = messageRepository.findAll();
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        try {
            Optional<Message> message = messageRepository.findById(id);
            return message.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message updatedMessage) {
        try {
            //Récupère de la base de données un message optionnel avec l'identifiant spécifié,
            Optional<Message> messageData = messageRepository.findById(id);
            if (messageData.isPresent()) {
                Message existingMessage = messageData.get();
                existingMessage.setType(updatedMessage.getType());
                existingMessage.setContenu(updatedMessage.getContenu());
                existingMessage.setEmetteur(updatedMessage.getEmetteur());
                existingMessage.setRecepteurs(updatedMessage.getRecepteurs());

                // Update other fields as needed

                Message updatedMessageObj = messageRepository.save(existingMessage);
                return new ResponseEntity<>(updatedMessageObj, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable Long id) {
        try {
            messageRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/limit")
    public ResponseEntity<List<Message>> getAllMessages(@RequestParam(defaultValue = "10") int limit) {
        try {
            // Create a Pageable object with the specified limit
            //présenter un long contenu, ou des suites de produits en plusieurs pages successives.
            Pageable pageable = PageRequest.of(0, limit);

            // Retrieve the messages using findAll with Pageable
            List<Message> messages = messageRepository.findAll(pageable).getContent();

            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
