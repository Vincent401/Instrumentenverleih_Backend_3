package com.example.Instrumentenverleih_Backend_3.kunden;

import com.example.Instrumentenverleih_Backend_3.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/kunden")
public class KundeController {
    @Autowired
    KundeRepository kundeRepository;
    @Autowired
    private EmailSenderService senderService;

    @PostMapping("")
    public void createKunde(@RequestBody kunde kunde){
        kundeRepository.save(kunde);
        sendMail(kunde.getEmail(), kunde.getName());
    }

    public void sendMail(String toEmail, String Name){
        senderService.sendEmail(toEmail,
                "Vielen dank für ihre Bestellung",
                "Hallo " + Name +", \n \n" +
                        "vielen dank, dass sie sich für ein Instrument von uns entschieden haben. \n" +
                        "Ihr Instrument wird schnellstmöglich vorbereitet und sollte sie innerhalb von wenigen Werktagen erreichen. \n" +
                        "\n Liebe Grüße ihr \nInstrumentenverleih Mannheim");
    }

    @GetMapping("/{kundenId}")
    public kunde readKunde(@PathVariable Long kundenId){
        Optional<kunde> kunde = kundeRepository.findById(kundenId);
        if (kunde.isPresent()){
            return kunde.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kunde mit dieser Id nicht gefunden.");
    }

    @PutMapping("/{kundenId}")
    public void updateKunde(@PathVariable Long kundenId, @RequestBody kunde kundeUpdate) {
        Optional<kunde> kunde = kundeRepository.findById(kundenId);
        if(!kunde.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kunde mit dieser Id nicht gefunden.");
        }

        kunde kundeInstance = kunde.get();
        kundeInstance.setName(kundeUpdate.getName());
        kundeInstance.setEmail(kundeUpdate.getEmail());
        kundeInstance.setTelefonnummer(kundeUpdate.getTelefonnummer());
        kundeInstance.setStraße(kundeUpdate.getStraße());
        kundeInstance.setPlz(kundeUpdate.getPlz());
        kundeInstance.setStadt(kundeUpdate.getStadt());
        kundeInstance.setIban(kundeUpdate.getIban());
        kundeInstance.setInstrument(kundeUpdate.getInstrument());
        kundeRepository.save(kundeInstance);
    }

    @DeleteMapping("/{kundenId}")
    public void deleteKunde(@PathVariable Long kundenId) {
        Optional<kunde> kunde = kundeRepository.findById(kundenId);
        if (kunde.isPresent()) {
            kundeRepository.deleteById(kundenId);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kunde mit dieser Id nicht gefunden.");
    }
}
