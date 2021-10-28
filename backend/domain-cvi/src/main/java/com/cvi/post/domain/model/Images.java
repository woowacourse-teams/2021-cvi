package com.cvi.post.domain.model;

import com.cvi.exception.NotFoundException;
import com.cvi.image.domain.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Images {

    @OrderBy("createdAt asc")
    @OneToMany(mappedBy = "post")
    private final List<Image> images = new ArrayList<>();

    public void addAll(List<Image> images, Post post) {
        if (images.isEmpty()) {
            throw new NotFoundException("추가하려는 이미지 리스트가 비어있습니다.");
        }
        addImages(images, post);
    }

    private void addImages(List<Image> images, Post post) {
        for (Image image : images) {
            image.assignPost(post);
            addImage(image);
        }
    }

    private void addImage(Image image) {
        if (!images.contains(image)) {
            images.add(image);
        }
    }

    public void delete(Long imageId) {
        final Image foundImage = findById(imageId);
        images.remove(foundImage);
    }

    private Image findById(Long imageId) {
        return images.stream()
            .filter(image -> image.isSameAs(imageId))
            .findAny()
            .orElseThrow(() -> new NotFoundException("찾을 수 없는 이미지 입니다."));
    }

    public int size() {
        return images.size();
    }

    public List<String> getS3PathsOfAllImages() {
        return images.stream()
            .map(Image::getName)
            .collect(Collectors.toList());
    }
}
