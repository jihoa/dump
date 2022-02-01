package org.springframework.samples.petclinic.audit;

public interface ListenerService {
	ListenerEntity getEntity(Long id);

	void saveEntity(ListenerEntity listenerEntity);

	void updateEntity(ListenerEntity listenerEntity);

	void removeEntity(ListenerEntity listenerEntity);
}
