package com.abhishekvermaa10.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abhishekvermaa10.dto.AttachmentDTO;
import com.abhishekvermaa10.dto.MailDTO;
import com.abhishekvermaa10.service.AsyncMailService;
import com.abhishekvermaa10.service.MailService;

/**
 * @author abhishekvermaa10
 */
@Service
public class MailServiceImpl implements MailService {

	private final AsyncMailService asyncMailService;
	@Value("${initiated.message}")
	private String initiatedMessage;

	public MailServiceImpl(AsyncMailService asyncMailService) {
		this.asyncMailService = asyncMailService;
	}

	@Override
	public String sendBasicMail(MailDTO mailDTO) {
		asyncMailService.sendEmail(mailDTO)
			.thenAccept(System.out::println)
			.exceptionally(e -> {
				e.printStackTrace();
				return null;
			});
		return String.format(initiatedMessage, LocalDateTime.now());
	}

	@Override
	public String sendAdvancedMail(MailDTO mailDTO, Resource attachment) {
		asyncMailService.sendEmailWithAttachment(mailDTO, attachment)
				.thenAccept(System.out::println)
				.exceptionally(e -> {
					e.printStackTrace();
					return null;
				});
		return String.format(initiatedMessage, LocalDateTime.now());
	}

	@Override
	public String sendAdvancedMail(MailDTO mailDTO, List<MultipartFile> attachments) {
		List<AttachmentDTO> attachmentDataList = convertAttachments(attachments);
		asyncMailService.sendEmailWithAttachment(mailDTO, attachmentDataList)
				.thenAccept(System.out::println)
				.exceptionally(e -> {
					e.printStackTrace();
					return null;
				});
		return String.format(initiatedMessage, LocalDateTime.now());
	}
	
	private List<AttachmentDTO> convertAttachments(List<MultipartFile> attachments) {
		if (Objects.isNull(attachments)) {
			return new ArrayList<>();
		}
		return attachments.stream()
				.map(file -> {
					try {
						return new AttachmentDTO(file.getOriginalFilename(), file.getContentType(), file.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				})
				.toList();
	}

}
