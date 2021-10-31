package org.ok.starfish.data.content.provider.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplicationLine {

    @Getter
    private String name;

    @Getter
    private String categoryNames;
}
