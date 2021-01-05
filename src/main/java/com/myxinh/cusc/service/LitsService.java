package com.myxinh.cusc.service;

import com.myxinh.cusc.domain.Lits;
import com.myxinh.cusc.repository.LitsRepository;
import com.myxinh.cusc.repository.UserRepository;
import com.myxinh.cusc.service.dto.ui.LitsDTO;
import com.myxinh.cusc.service.mapper.LitsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LitsService {

    @Autowired
    private LitsRepository litsRepository;

    @Autowired
    private UserRepository userRepository;

    public Lits addLits(LitsDTO litsDTO){
        Lits litsAdd = LitsConverter.convertToDomain(litsDTO);
        userRepository.findUserEntityByUsername(litsDTO.getUsername()).ifPresent(litsAdd::setUser);
        return litsRepository.save(litsAdd);
    }

    public Optional<Lits> updateLits(LitsDTO litsDTO){
        Optional<Lits> litsFind = litsRepository.findById(litsDTO.getLits_id());
        Lits newLits = new Lits();
        if (litsFind.isPresent()){
            newLits = LitsConverter.covertToViews(litsDTO);
            newLits.setUser(litsFind.get().getUser());
            litsRepository.save(newLits);
        }
        return Optional.of(newLits);
    }

    public void deleteLits(int litsId){
        litsRepository.findById(litsId).ifPresent(
                lits -> {litsRepository.delete(lits);}
        );
    }
    public Optional<Lits> findById(int litsId){
        return litsRepository.findById(litsId);
    }

}
