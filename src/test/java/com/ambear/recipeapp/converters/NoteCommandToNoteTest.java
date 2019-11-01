package com.ambear.recipeapp.converters;

import com.ambear.recipeapp.commands.NoteCommand;
import com.ambear.recipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandToNoteTest {

  private static final Long LONG_VALUE = 1L;
  private static final String DESCRIPTION = "description";

  NoteCommandToNote converter;

  @BeforeEach
  void setUp() {
    converter = new NoteCommandToNote();
  }

  @Test
  void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  void testEmptyObject() {
    assertNotNull(converter.convert(new NoteCommand()));
  }

  @Test
  void convert() {
    NoteCommand noteCommand = new NoteCommand();
    noteCommand.setId(LONG_VALUE);
    noteCommand.setRecipeNote(DESCRIPTION);

    Note note = converter.convert(noteCommand);

    assertNotNull(note);
    assertEquals(note.getId(), LONG_VALUE);
    assertEquals(note.getRecipeNote(), DESCRIPTION);
  }
}