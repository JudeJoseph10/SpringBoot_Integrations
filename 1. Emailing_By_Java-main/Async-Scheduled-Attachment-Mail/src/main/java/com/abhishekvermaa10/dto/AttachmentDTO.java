package com.abhishekvermaa10.dto;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author abhishekvermaa10
 */
public record AttachmentDTO(String fileName, String contentType, byte[] data) {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + Objects.hash(contentType, fileName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttachmentDTO other = (AttachmentDTO) obj;
		return Objects.equals(contentType, other.contentType) && Arrays.equals(data, other.data)
				&& Objects.equals(fileName, other.fileName);
	}

	@Override
	public String toString() {
		return "AttachmentDTO [fileName=" + fileName + ", contentType=" + contentType + ", data="
				+ Arrays.toString(data) + "]";
	}

}
