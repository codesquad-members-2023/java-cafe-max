package kr.codesqaud.cafe.user.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.user.domain.UserEntity;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;
import kr.codesqaud.cafe.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * 회원 정보 등록하기(회원 가입)
	 * @param user 회원 가입 정보
	 * @throws IllegalArgumentException 이미 등록된 회원 ID을 중복하여 등록한 경우 Exception 발생
	 */
	public void addUser(UserEntity user) throws UserIdDuplicateException {
		Optional<UserEntity> existUserEntity = repository.findAll()
			.stream()
			.filter(existUser -> existUser.getUserId().equals(user.getUserId()))
			.findAny();

		if (existUserEntity.isPresent()) {
			throw new UserIdDuplicateException(existUserEntity.get().getUserId());
		}

		repository.save(user);
	}

	public UserEntity performSignIn(String userId, String password) throws UserDoesNotMatchException {
		try {
			UserEntity user = findByUserId(userId);
			if (password.equals(user.getPassword())) {
				return user;
			}
		} catch (UserNotExistException e) {
			throw new UserDoesNotMatchException();
		}
		throw new UserDoesNotMatchException();
	}

	/**
	 * 회원 목록 검색
	 * @return 회원 목록이 담긴 List
	 */
	public List<UserEntity> findAllUsers() {
		return repository.findAll();
	}

	/**
	 * 회원 정보 검색
	 * @param userId 검색할 회원 ID
	 * @return ID에 해당하는 회원 정보
	 * @throws NoSuchElementException 존재하지 않는 회원을 검색한 경우 Exception 발생
	 */
	public UserEntity findByUserId(String userId) throws UserNotExistException {
		Optional<UserEntity> userToFind = repository.findByUserId(userId);

		if (userToFind.isPresent()) {
			return userToFind.get();
		} else {
			throw new UserNotExistException(userId);
		}
	}

	/**
	 * 회원 정보 수정
	 * @param user 수정할 회원 정보
	 * @throws UserDoesNotMatchException 존재하지 않는 회원의 정보를 수정하거나, 비밀번호가 일치하지 않는 경우 Exception 발생
	 */
	public void updateUser(UserEntity user) throws UserDoesNotMatchException {
		if (repository.update(user)) {
			return;
		}

		throw new UserDoesNotMatchException();
	}

}
