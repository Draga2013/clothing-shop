package com.sorin.sda.clothingshop.service;

import com.sorin.sda.clothingshop.model.Account;
import com.sorin.sda.clothingshop.model.Role;
import com.sorin.sda.clothingshop.repository.AccountRepository;
import com.sorin.sda.clothingshop.service.dto.AccountDTO;
import com.sorin.sda.clothingshop.service.mail.MailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService
{
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;

    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder, MailService mailService)
    {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Account account = accountRepository.findByEmail(email);
        if (account == null)
        {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new User(account.getEmail(), account.getPassword(), mapRolesToAuthorities(account.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role)
    {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    public void createAccount(AccountDTO accountDTO)
    {
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setRole(Role.CUSTOMER);

        accountRepository.save(account);
        try {
            mailService.sendMail("office@clothing.com", account.getEmail(),"Welcome to my aplication", "Welcome" );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Boolean accountExist(String email)
    {
        Account accountExisting = accountRepository.findByEmail(email);
        return accountExisting != null;
    }

    public Optional<User> getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(null != auth && auth.getPrincipal() instanceof User) {
            User user = (User)auth.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
