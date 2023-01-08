#version 400 core

layout (location = 0) in vec3 aVertexPosition;
layout (location = 1) in vec4 aColor;
layout (location = 2) in float aTextureID;
layout (location = 3) in vec2 aTextureCoord;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 vColor;
out float vTextureID;
out vec2 vTextureCoord;

void main() {

    vColor = aColor;
    vTextureID = aTextureID;
    vTextureCoord = aTextureCoord;

    gl_Position = uProjection * uView * vec4(aVertexPosition, 1.0);

}