// CustomUserDetailsService.java
package com.tmtb.pageon.config;

import com.tmtb.pageon.user.mapper.UserMapper;
import com.tmtb.pageon.user.model.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		UserVO vo = mapper.findById(id);
		if (vo == null) {
			throw new UsernameNotFoundException("존재하지 않는 사용자 입니다");
		}
		//2. UserDetails 객체에 해당정보를 담아서 리턴해 주어야 한다
		//권한은 1개 이지만 List 에 담아서
		List<GrantedAuthority> authList=new ArrayList<>();
		//Authority 는 role 앞에  "ROLE_" 를 붙여주여야 한다.
		authList.add(new SimpleGrantedAuthority("ROLE_"+vo.getUser_role()));
		for (GrantedAuthority authority : authList) {
			log.info("authority.getAuthority():{}",authority.getAuthority());
		}
		//Spring Security 가 제공하는 User 클래스는 UserDetails 인터페이스를 구현한 클래스 이다.
		// CustomUser 객체 생성 (UserVO를 포함시킨 커스터마이즈된 UserDetails)
		CustomUser customUser = new CustomUser(vo, vo.getId(), vo.getPw(), authList);

		// UserVO 객체를 Principal로 설정
		log.info("로그인된 사용자: " + vo.getName());  // 이름 로그 출력

		return customUser;
	}
}
