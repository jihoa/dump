package org.springframework.samples.petclinic.audit;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerServiceImpl implements ListenerService{

	private final ListenerRepository listenerRepository;

	@Override
	public ListenerEntity getEntity(Long id) {
		return listenerRepository.findById(id).get();
	}

	@Override
	public void saveEntity(ListenerEntity listenerEntity) {
		listenerRepository.save(listenerEntity);
	}

	@Override
	public void updateEntity(ListenerEntity listenerEntity) {
		ListenerEntity foundListener = listenerRepository.findById(listenerEntity.getId()).get();
		foundListener.setName(listenerEntity.getName());

		listenerRepository.save(foundListener);
	}

	@Override
	public void removeEntity(ListenerEntity listenerEntity) {
		listenerRepository.delete(listenerEntity);
	}
}
