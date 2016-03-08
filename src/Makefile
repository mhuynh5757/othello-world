JAVAC   = javac
SOURCES = $(wildcard *.java)
CLASSES = $(SOURCES:.java=.class)

all: $(CLASSES) OthelloFramework.jar

OthelloFramework.jar: $(CLASSES)
	jar cvmf Manifest.txt ../OthelloFramework.jar -C ../bin .

%.class: %.java
	$(JAVAC) -d ../bin $<
