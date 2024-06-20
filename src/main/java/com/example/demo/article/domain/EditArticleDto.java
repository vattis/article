package com.example.demo.article.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EditArticleDto {
    String title;
    String content;
    Long memberId;
    Long articleId;
    LocalDateTime editedDate;
    public static EditArticleDto of(Long memberId, Long articleId, String title, String content, LocalDateTime editedDate){
        return builder()
                .memberId(memberId)
                .articleId(articleId)
                .title(title)
                .content(content)
                .editedDate(editedDate)
                .build();
    }
}
