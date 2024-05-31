package com.example.demo.member.service;

import com.example.demo.member.domain.CustomMemberDetails;
import com.example.demo.member.domain.CustomMemberInfoDto;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(()->new UsernameNotFoundException("해당하는 유저가 없습니다."));
        CustomMemberInfoDto dto = mapper.map(member, CustomMemberInfoDto.class);
        return new CustomMemberDetails(dto);
    }
}
