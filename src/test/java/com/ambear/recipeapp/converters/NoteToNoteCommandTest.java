package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.NoteCommand;
import com.ambear.recipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteToNoteCommandTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";

  NoteToNoteCommand converter;

  @BeforeEach
  void setUp() {
    converter = new NoteToNoteCommand();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new Note()));
  }

  @Test
  void convert() {
    Note note = new Note();
    note.setId(LONG_VALUE);
    note.setRecipeNote(DESCRIPTION);

    NoteCommand noteCommand = converter.convert(note);

    assertNotNull(noteCommand);
    assertEquals(noteCommand.getId(), LONG_VALUE);
    assertEquals(noteCommand.getRecipeNote(), DESCRIPTION);

  }
}