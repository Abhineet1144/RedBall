#version 330 core
out vec4 FragColor;

in vec4 ourColor;
in vec2 TexCoord;
flat in int ourTId;

uniform sampler2D u_Textures[8]; // Array of 8 slots

void main()
{
    if (ourTId == 0) {
        FragColor = ourColor;
    } else {
        vec4 texColor;
        // Use a switch to pick the specific sampler
        // This satisfies the "constant expression" requirement
        switch(ourTId) {
            case 1:  texColor = texture(u_Textures[0], TexCoord); break;
            case 2:  texColor = texture(u_Textures[1], TexCoord); break;
            case 3:  texColor = texture(u_Textures[2], TexCoord); break;
            case 4:  texColor = texture(u_Textures[3], TexCoord); break;
            default: texColor = texture(u_Textures[0], TexCoord); break;
        }
        FragColor = texColor * ourColor;
    }
}