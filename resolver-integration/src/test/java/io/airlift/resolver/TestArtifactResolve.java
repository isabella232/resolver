/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.resolver;

import io.airlift.resolver.ArtifactResolver;
import io.airlift.resolver.DefaultArtifact;
import org.sonatype.aether.artifact.Artifact;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.airlift.resolver.ArtifactResolver.MAVEN_CENTRAL_URI;

// NOTE: Intellij does not resolver this class correctly since the resolver jar is generated by the assembler plugin
public class TestArtifactResolve
{
    @Test
    public void testResolveArtifacts()
            throws Exception
    {
        ArtifactResolver artifactResolver = new ArtifactResolver("target/local-repo", MAVEN_CENTRAL_URI);
        List<Artifact> artifacts = artifactResolver.resolveArtifacts(new DefaultArtifact("org.apache.maven:maven-core:3.0.4"));

        Assert.assertNotNull(artifacts, "artifacts is null");
        for (Artifact artifact : artifacts) {
            Assert.assertNotNull(artifact.getFile(), "Artifact " + artifact + " is not resolved");
        }
    }

    @Test
    public void testResolvePom()
    {
        File pomFile = new File("src/test/poms/maven-core-3.0.4.pom");
        Assert.assertTrue(pomFile.canRead());

        ArtifactResolver artifactResolver = new ArtifactResolver("target/local-repo", MAVEN_CENTRAL_URI);
        List<Artifact> artifacts = artifactResolver.resolvePom(pomFile);

        Assert.assertNotNull(artifacts, "artifacts is null");
        for (Artifact artifact : artifacts) {
            Assert.assertNotNull(artifact.getFile(), "Artifact " + artifact + " is not resolved");
        }
    }
}