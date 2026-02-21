#version 330 core
out vec4 FragColor;

in vec4 ourColor;
in vec2 TexCoord;
flat in int ourTId;

uniform sampler2D texture1;

void main()
{
    if (ourTId == 0) {
        FragColor = ourColor;
    } else {
        FragColor = texture(texture1, TexCoord) * ourColor;
    }
}