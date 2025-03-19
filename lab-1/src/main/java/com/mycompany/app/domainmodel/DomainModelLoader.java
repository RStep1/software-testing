package com.mycompany.app.domainmodel;

import com.mycompany.app.domainmodel.entity.Meal;
import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.entity.animate.Alien;
import com.mycompany.app.domainmodel.entity.animate.Entity;
import com.mycompany.app.domainmodel.entity.animate.ExpressionType;
import com.mycompany.app.domainmodel.entity.animate.Human;
import com.mycompany.app.domainmodel.entity.intangible.Disease;
import com.mycompany.app.domainmodel.entity.intangible.Message;
import com.mycompany.app.domainmodel.experiments.MiceExperiments;
import com.mycompany.app.domainmodel.experiments.Result;
import com.mycompany.app.domainmodel.memento.Caretaker;
import com.mycompany.app.domainmodel.memento.HistoryMaker;
import com.mycompany.app.domainmodel.memento.Originator;
import com.mycompany.app.domainmodel.mice.ComulativeEffect;
import com.mycompany.app.domainmodel.mice.MiceProjectionManager;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
public class DomainModelLoader {

    public static void run() {
        MiceProjectionManager miceProjection = new MiceProjectionManager();
        Mouse earthMouse = miceProjection.getEarthMouse();

        ComulativeEffect comulativeEffect = new ComulativeEffect();

        List<Message> questions = new ArrayList<>();
        questions.addAll(
            Arrays.asList(
                new Message("Why are people born?", ExpressionType.QUESTION),
                new Message("Why do they die?", ExpressionType.QUESTION),
                new Message("Why do they spend so much time on electronic watches?", ExpressionType.QUESTION),
                new Message("What is meaning of life?", ExpressionType.QUESTION)
        ));


        Entity arthur = new Human("Arthur", 1);
        Entity slartibartfast = new Alien("Slartibartfast", 1);

        Message question1 = arthur.request("Mice?");
        slartibartfast.communicate(question1, "Indeed, Earthman.");
        Message question2 = arthur.request("""
            Look, sorry, are we talking about the little white furry things with \
            the cheese fixation and women standing on tables screaming in early \
            sixties sitcoms?
            """);
        slartibartfast.communicate(question2, String.format("""
            Earthman, it is sometimes hard to follow your mode of \
            speech. Remember I have been asleep inside this planet of Magrathea \
            for five million years and know little of these early sixties sitcoms of \
            which you speak. These creatures you call %s, you see, they are not \
            quite as they appear. They are merely %s of %s. The \
            whole business with the cheese and the squeaking is just a front.
            """, earthMouse.getDescription(), earthMouse, miceProjection.getMouse()));

        Message answer2 = slartibartfast.communicate(question2, String.format("""
                They’ve been experimenting on you, I’m afraid.
                """));

        
        Message message1 = arthur.communicate(answer2, String.format("""
                Ah no, “I see the source of the misunderstanding now. \
                No, look, you see what happened was that we used to do experiments \
                on them.
                """));
        List<Result> experimentsResults = new ArrayList<>();
        experimentsResults.add(MiceExperiments.behaviorResearch(earthMouse, Place.MAZE));
        experimentsResults.add(MiceExperiments.makePavlovExperiment(earthMouse));
        experimentsResults.add(MiceExperiments.teachMouse(earthMouse));
        experimentsResults.addAll(MiceExperiments.makeTests(earthMouse));
        log.info("Results from experiments:");
        boolean isExperimentsImportent = false;
        for (int i = 0; i < experimentsResults.size(); i++) {
            log.info(experimentsResults.get(i).conclusion());
            if (experimentsResults.get(i).isImportant()) {
                isExperimentsImportent = true;
            }
        }

        if (isExperimentsImportent) {
            arthur.communicate(answer2, """
                    From our observations of their behavior we were able to learn all\
                    sorts of things about our own...
                    """
            );
        }

        slartibartfast.communicate(message1, "Such subtlety... one has to admire it.");
        Message question3 = arthur.request("What?");
        
        slartibartfast.communicate(question3,
             "How better to disguise their real natures, and how better to guide your thinking.");
        comulativeEffect.applyToEffect(earthMouse.run(Place.MAZE, false));
        comulativeEffect.applyToEffect(earthMouse.eat(Meal.CHEESE, false));
        comulativeEffect.applyToEffect(earthMouse.die(Disease.MYXOMATOSIS));

        slartibartfast.communicate(question3,
            "If it’s finely calculated the cumulative effect is " + comulativeEffect.getApproxDescription());

        Message answer3 = slartibartfast.communicate(question3, 
            "You see, Earthman, they really are particularly " + miceProjection.getMouse() + ". Your planet and people have " + //
            "formed the matrix of an organic computer running a ten-million-year " + //
            "research program…. Let me tell you the whole story. It’ll take a little " + //
            "time.");

        Message message3 = arthur.communicate(answer3, "Time, is not currently one of my problems.");

        StringBuilder mostPopularQuestions = new StringBuilder();
        questions.forEach(x -> {
                mostPopularQuestions.append(x.getMessage());
                mostPopularQuestions.append(" ");
            }
        );
        
        slartibartfast.communicate(message3,
                "There are of course many problems" + //
                "connected with life, of which" + //
                "some of the most popular are " + mostPopularQuestions.toString()); 
        

        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker(originator);
        HistoryMaker historyMaker = new HistoryMaker(originator, caretaker);
        historyMaker.makeHistoryAboutMice(earthMouse, miceProjection.getMouse(), questions);

        Message story = slartibartfast.tellStory(caretaker);
        log.info(story.getMessage());
    }
}
