package com.challenge.disney.services;

import com.challenge.disney.entities.Photo;
import com.challenge.disney.repositories.PhotoRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Jonathan
 */
@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepo; 
	
	public Photo savePhoto(MultipartFile archive) throws IOException{
		
		if (archive != null){
			
			try{
				Photo photo = new Photo();
				photo.setName(archive.getName());
				photo.setMime(archive.getContentType());
				photo.setContainer(archive.getBytes());
				
				photoRepo.save(photo); 
				return photo; 
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null; 
	}
	
}
