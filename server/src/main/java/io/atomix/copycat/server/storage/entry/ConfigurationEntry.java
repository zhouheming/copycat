/*
 * Copyright 2015 the original author or authors.
 *
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
package io.atomix.copycat.server.storage.entry;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.SerializeWith;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.util.Assert;
import io.atomix.catalyst.util.ReferenceManager;
import io.atomix.copycat.server.state.Member;

import java.util.Collection;

/**
 * Configuration entry.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
@SerializeWith(id=221)
public class ConfigurationEntry extends Entry<ConfigurationEntry> {
  private Collection<Member> active;
  private Collection<Member> passive;
  private Collection<Member> reserve;

  public ConfigurationEntry() {
  }

  public ConfigurationEntry(ReferenceManager<Entry<?>> referenceManager) {
    super(referenceManager);
  }

  /**
   * Returns the active members.
   *
   * @return The active members.
   */
  public Collection<Member> getActiveMembers() {
    return active;
  }

  /**
   * Sets the active members.
   *
   * @param members The active members.
   * @return The configuration entry.
   * @throws NullPointerException if {@code members} is null
   */
  public ConfigurationEntry setActiveMembers(Collection<Member> members) {
    this.active = Assert.notNull(members, "members");
    return this;
  }

  /**
   * Returns the passive members.
   *
   * @return The passive members.
   */
  public Collection<Member> getPassiveMembers() {
    return passive;
  }

  /**
   * Sets the passive members.
   *
   * @param members The passive members.
   * @return The configuration entry.
   * @throws NullPointerException if {@code members} is null
   */
  public ConfigurationEntry setPassiveMembers(Collection<Member> members) {
    this.passive = Assert.notNull(members, "members");
    return this;
  }

  /**
   * Returns the reserve members.
   *
   * @return The reserve members.
   */
  public Collection<Member> getReserveMembers() {
    return reserve;
  }

  /**
   * Sets the reserve members.
   *
   * @param members The reserve members.
   * @return The configuration entry.
   * @throws NullPointerException if {@code members} is null
   */
  public ConfigurationEntry setReserveMembers(Collection<Member> members) {
    this.reserve = Assert.notNull(members, "members");
    return this;
  }

  @Override
  public void writeObject(BufferOutput buffer, Serializer serializer) {
    super.writeObject(buffer, serializer);
    serializer.writeObject(active, buffer);
    serializer.writeObject(passive, buffer);
    serializer.writeObject(reserve, buffer);
  }

  @Override
  public void readObject(BufferInput buffer, Serializer serializer) {
    super.readObject(buffer, serializer);
    active = serializer.readObject(buffer);
    passive = serializer.readObject(buffer);
    reserve = serializer.readObject(buffer);
  }

  @Override
  public String toString() {
    return String.format("%s[index=%d, term=%d, active=%s, passive=%s, reserve=%s]", getClass().getSimpleName(), getIndex(), getTerm(), active, passive, reserve);
  }

}
