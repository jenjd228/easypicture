package com.easyPicture.easyPicture.repository;

import com.easyPicture.easyPicture.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

}
