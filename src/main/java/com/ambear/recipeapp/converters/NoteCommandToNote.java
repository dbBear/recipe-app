package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.NoteCommand;
import com.ambear.recipeapp.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote
    implements Converter<NoteCommand, Note>
{

  @Synchronized
  @Nullable
  @Override
  public Note convert(NoteCommand source) {
    if(source == null) {
      return null;
    }

    Note note = new Note();
    note.setId(source.getId());
    note.setRecipeNote(source.getRecipeNote());

    return note;
  }
}
