package com.example.eduability;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadListenActivity extends AppCompatActivity {

    ListView listStories;

    String[] titles = {
            "The Happy Cat",
            "The Kind Elephant",
            "The Clever Fox"
    };

    String[] stories = {

                    "Once upon a time, in a bright little town, there lived a fluffy orange cat named Mimi. Mimi wasn’t just any cat—she was the happiest cat in the world! Every morning, she woke up with a big stretch, a tiny yawn, and a cheerful “Meow!” to greet the day.\n" +
                    "\n" +
                    "Mimi loved three things the most:\n" +
                    "✨ Playing with butterflies\n" +
                    "✨ Helping her friends\n" +
                    "✨ And spreading smiles everywhere she went\n" +
                    "\n" +
                    "One sunny morning, Mimi heard a tiny cry. It was coming from the garden. She followed the sound and found a little bird who had fallen from a small bush. The bird looked scared.\n" +
                    "\n" +
                    "“Don’t worry!” Mimi said kindly. “I’ll help you.”\n" +
                    "\n" +
                    "Very gently, Mimi guided the bird back to its nest. The bird chirped happily, and the mama bird thanked Mimi with a sweet song.\n" +
                    "\n" +
                    "Feeling proud, Mimi continued her day. She helped a puppy find his ball, shared milk with a hungry kitten, and even made a shy bunny laugh by chasing her own tail!\n" +
                    "\n" +
                    "By evening, the whole town was smiling because of Mimi.\n" +
                    "\n" +
                    "When the stars came out, Mimi curled up in her cozy basket. The moonlight shined softly on her fur as she drifted to sleep, happy and proud.\n" +
                    "\n" +
                    "Because Mimi knew one magical secret:\n" +
                    "\n" +
                    "Happiness grows when you share it.\n" +
                    "\n" +
                    "And that’s why Mimi, the little orange cat, would always be the happiest cat in the world.",
            "Once upon a time, in a big green jungle, there lived a gentle elephant named Tumbo. Tumbo was the largest animal in the forest, but he also had the softest heart. All the animals loved him because he was always ready to help.\n" +
                    "\n" +
                    "One warm morning, Tumbo was walking near the river when he saw a tiny rabbit crying.\n" +
                    "\n" +
                    "“Why are you sad, little friend?” Tumbo asked kindly.\n" +
                    "\n" +
                    "“My house was washed away by the rain,” the rabbit sniffed.\n" +
                    "\n" +
                    "Tumbo thought for a moment. Then he smiled and said, “Don’t worry. I will help you.”\n" +
                    "With his strong trunk, Tumbo gathered leaves, sticks, and soft grass. Soon, he built a new, cozy home for the rabbit. The rabbit jumped happily and hugged Tumbo’s leg.\n" +
                    "\n" +
                    "Later that day, Tumbo heard another cry — this time from a monkey stuck on a tall branch that had broken.\n" +
                    "\n" +
                    "“Hold on!” Tumbo called.\n" +
                    "He lifted his trunk high and helped the monkey climb down safely. The monkey clapped with joy and thanked him.\n" +
                    "\n" +
                    "In the evening, it became very hot and all the animals felt tired. Tumbo had a wonderful idea. He filled his trunk with cool river water and sprayed it gently over everyone like rain! \uD83C\uDF27\uFE0F\n" +
                    "All the animals laughed, danced, and felt refreshed.\n" +
                    "\n" +
                    "That night, the jungle was peaceful and happy. Everyone gathered around Tumbo and said,\n" +
                    "“Thank you for being kind. You make this jungle a better place.”\n" +
                    "\n" +
                    "Tumbo smiled softly. He didn’t help to be praised. He helped because kindness made his heart glow.\n" +
                    "\n" +
                    "And from that day on, Tumbo was known as\n" +
                    "Tumbo, the Kind Elephant — the friend of everyone.",
            "Once upon a time, in a peaceful forest, there lived a clever little fox named Fino. Fino wasn’t the biggest or the strongest animal, but he was the smartest. He always used his brain to solve problems.\n" +
                    "\n" +
                    "One hot afternoon, Fino was walking when he heard loud roaring. A big, angry lion was stuck in a deep hunting trap! The lion shouted,\n" +
                    "“Help! Someone please help me!”\n" +
                    "\n" +
                    "All the animals were scared to go near him. But Fino bravely stepped forward.\n" +
                    "\n" +
                    "“If I help you, will you promise not to hurt anyone?” Fino asked.\n" +
                    "\n" +
                    "The lion nodded. “I promise.”\n" +
                    "\n" +
                    "Fino thought for a moment. Then he got an idea. He asked the monkeys to bring long vines and the elephants to pull them. Working together, they slowly lifted the lion out of the trap.\n" +
                    "\n" +
                    "The lion was free! But soon, he forgot his promise.\n" +
                    "He growled and tried to scare the animals again.\n" +
                    "\n" +
                    "Fino stepped in front and said calmly,\n" +
                    "“If you scare us again, no one will help you next time. Strength is good, but kindness is greater.”\n" +
                    "\n" +
                    "The lion felt ashamed. He finally understood.\n" +
                    "\n" +
                    "From that day on, the lion became gentle and thankful. He protected the forest and always remembered the little clever fox who taught him a big lesson.\n" +
                    "\n" +
                    "And Fino? He continued to help everyone with his smart thinking and brave heart."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_listen);

        listStories = findViewById(R.id.listStories);

        StoryAdapter adapter = new StoryAdapter(this, titles);
        listStories.setAdapter(adapter);

        listStories.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, StoryActivity.class);
            intent.putExtra("title", titles[position]);
            intent.putExtra("story", stories[position]);
            startActivity(intent);
        });
    }
}
