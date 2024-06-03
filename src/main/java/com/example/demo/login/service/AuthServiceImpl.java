package com.example.demo.login.service;

import com.example.demo.login.domain.LoginRequestDto;
import com.example.demo.login.jwt.JwtUtil;
import com.example.demo.member.domain.CustomMemberInfoDto;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public String login(LoginRequestDto dto) {
        String memberId = dto.getMemberId();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByMemberId(memberId);
        if(member==null){
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        if(!encoder.matches(password, member.getMemberPw())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomMemberInfoDto info = modelMapper.map(member, CustomMemberInfoDto.class);
        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }
}